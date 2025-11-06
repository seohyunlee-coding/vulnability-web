from django.shortcuts import render, redirect, get_object_or_404
from django.contrib.auth.decorators import login_required
from django.contrib.auth import login as auth_login
from django.contrib.auth.forms import UserCreationForm
from django.db import connection
from django.views.decorators.csrf import csrf_exempt
from django.template import Template, Context

from .models import Post
from .forms import PostForm


@login_required
@csrf_exempt  # CSRF 보호 비활성화 (교육용 취약점)
def create_post(request):
    """
    CSRF 취약점: @csrf_exempt 데코레이터로 CSRF 보호 제거
    공격자가 외부 사이트에서 이 엔드포인트로 POST 요청 가능
    """
    if request.method == 'POST':
        form = PostForm(request.POST)
        if form.is_valid():
            p = form.save(commit=False)
            p.author = request.user
            p.save()
            return redirect('home')
    else:
        form = PostForm()
    return render(request, 'board/create_post.html', {'form': form})


@login_required
def my_posts(request):
    """Show posts authored by the logged-in user."""
    posts = Post.objects.filter(author=request.user).order_by('-created_at')
    return render(request, 'board/my_posts.html', {'posts': posts})


def signup(request):
    """User registration using Django's built-in UserCreationForm.

    On success the new user is automatically logged in and redirected to home.
    """
    if request.method == 'POST':
        form = UserCreationForm(request.POST)
        if form.is_valid():
            user = form.save()
            # Log the user in after successful signup
            auth_login(request, user)
            return redirect('home')
    else:
        form = UserCreationForm()
    return render(request, 'registration/signup.html', {'form': form})

def home(request):
    """
    SQL Injection 취약점: 사용자 입력을 직접 SQL 쿼리에 삽입
    테스트 예시: ?query=' OR '1'='1
    """
    query = request.GET.get('query', '')
    if query:
        # 취약한 Raw SQL 사용 (교육용)
        with connection.cursor() as cursor:
            # SQL Injection 취약점: 사용자 입력을 직접 쿼리에 삽입
            sql = f"SELECT * FROM board_post WHERE title LIKE '%{query}%' ORDER BY created_at DESC"
            cursor.execute(sql)
            columns = [col[0] for col in cursor.description]
            posts = []
            for row in cursor.fetchall():
                # UNION 공격 시 다른 테이블 데이터가 섞여 들어올 수 있음
                # 컬럼 개수가 맞으면 Post 객체로 변환
                try:
                    posts.append(Post(
                        id=row[0],
                        author_id=row[1],
                        title=row[2],
                        body=row[3],  # UNION 공격 시 여기에 비밀번호 등이 들어올 수 있음
                        created_at=row[4]
                    ))
                except:
                    # 에러 발생 시 raw 데이터를 딕셔너리로 전달
                    pass
    else:
        posts = Post.objects.all().order_by('-created_at')
    return render(request, 'board/home.html', {'posts': posts, 'query': query})

def csrf_attack_demo(request):
    """
    CSRF 공격 데모 페이지
    이 페이지에서 버튼을 클릭하면 사용자 모르게 게시물이 작성됨
    """
    return render(request, 'board/csrf_attack_demo.html')
