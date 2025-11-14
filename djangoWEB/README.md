# 🔒 Vulnerability Web - 보안 취약점 학습 프로젝트

이 저장소는 웹 보안 취약점을 학습하기 위한 교육용 Django 웹 애플리케이션입니다. XSS, SQL Injection, CSRF 등 주요 보안 취약점이 의도적으로 구현되어 있어 실습과 연구를 통해 안전한 코딩 방법을 배울 수 있습니다.

⚠️ **경고**: 이 프로젝트는 교육 목적으로만 사용할 것. 실제 프로덕션 환경에 절대 배포하지 말 것.

## 📂 프로젝트 구성
```
django-vulnability-web/
├── README.md
├── VULNERABILITIES.md          # 상세 취약점 테스트 가이드
├── requirements.txt
├── manage.py
├── db.sqlite3
├── board/                      # 메인 앱
│   ├── admin.py
│   ├── forms.py
│   ├── models.py
│   ├── views.py                # 취약한 뷰 함수들
│   ├── urls.py
│   └── migrations/
├── templates/                  # HTML 템플릿
│   ├── board/
│   │   ├── base.html
│   │   ├── home.html
│   │   ├── create_post.html
│   │   ├── my_posts.html
│   │   └── csrf_attack_demo.html
│   └── registration/
│       ├── login.html
│       └── signup.html
└── vulnsite/                   # Django 프로젝트 설정
    ├── settings.py
    ├── urls.py
    └── wsgi.py
```

## ⚙️ 개발 환경 및 필수 도구
- Python 3.8 이상
- Django 4.2 이상
- SQLite3


## 🔧 프로젝트 빌드·실행 (Windows 명령 예시)
### 1) 가상환경 생성 및 활성화 (권장):
```powershell
cd "C:\Users\dev\Desktop\new vuln\django-vulnability-web"
python -m venv venv
.\venv\Scripts\Activate.ps1
```

### 2) 의존성 설치:
```powershell
pip install -r requirements.txt
```

### 3) 데이터베이스 마이그레이션:
```powershell
python manage.py migrate
```

### 4) 관리자 계정 생성 (선택):
```powershell
python manage.py createsuperuser
```

### 5) 개발 서버 실행:
```powershell
python manage.py runserver
```
- 브라우저에서 http://127.0.0.1:8000 접속

## 🚀 주요 기능
- 회원 가입  http://127.0.0.1:8000/signup/
- 로그인  http://127.0.0.1:8000/accounts/login/
- 내 글 조회  http://127.0.0.1:8000/my_posts/
- 새 글 생성  http://127.0.0.1:8000/create/
- 홈 화면 (글 조회 및 검색)  http://127.0.0.1:8000/



## 🛠️ 구현된 취약점

### 1. XSS (Cross-Site Scripting)
**위치**: `templates/board/home.html`
- 사용자 입력이 `|safe` 필터로 렌더링되어 스크립트가 실행됨
- **테스트**: 게시물 제목에 `<script>alert('XSS')</script>` 입력

### 2. SQL Injection
**위치**: `board/views.py` - `home()` 함수
- Raw SQL 쿼리에 사용자 입력이 직접 삽입됨
- **테스트**: 검색창에 `' OR '1'='1` 또는 `' UNION SELECT ...` 입력

### 3. CSRF (Cross-Site Request Forgery)
**위치**: `board/views.py` - `create_post()` 함수
- `@csrf_exempt` 데코레이터로 CSRF 보호 비활성화
- **테스트**: 로그인 후 `csrf_attack_demo.html` 페이지에서 버튼 클릭

## 📚 사용 기술 / 라이브러리
- **Backend**: Django 4.2+
- **Database**: SQLite3
- **Frontend**: HTML5, CSS3, 바닐라 JavaScript
- **템플릿 엔진**: Django Template Language


## ⚖️ 면책 조항
- 이 프로젝트는 순수하게 교육 목적으로 제작되었음
- 로컬 환경에서만 테스트할 것


## 📚 추가 학습 자료
- [OWASP Top 10](https://owasp.org/www-project-top-ten/)
- [Django Security Guide](https://docs.djangoproject.com/en/stable/topics/security/)
- [Web Security Academy](https://portswigger.net/web-security)


## 👩‍💻 작성자/연락처
- 이름: 이서현
- 이메일: cwijiq3085@gmail.com
- GitHub: https://github.com/seohyunlee-coding

