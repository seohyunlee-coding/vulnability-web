from django.urls import path
from . import views

urlpatterns = [
    path('', views.home, name='home'),
    path('create/', views.create_post, name='create_post'),
    path('my_posts/', views.my_posts, name='my_posts'),
    path('signup/', views.signup, name='signup'),
    path('csrf-demo/', views.csrf_attack_demo, name='csrf_demo'),  # CSRF 공격 데모
]
