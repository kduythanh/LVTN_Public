import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:lvtn_app/pages/admin/account_management.dart';
import 'package:lvtn_app/pages/admin/change_password.dart';
import 'package:lvtn_app/pages/admin/homepage.dart';
import 'package:lvtn_app/pages/admission_info.dart';
import 'package:lvtn_app/pages/hocsinh/hocsinh_detail.dart';
import 'package:lvtn_app/pages/hocsinh/homepage.dart';
import 'package:lvtn_app/pages/homepage.dart';
import 'package:lvtn_app/pages/information.dart';
import 'package:lvtn_app/pages/login.dart';
import 'package:lvtn_app/pages/page_title.dart';
import 'package:lvtn_app/pages/search_result.dart';
import 'package:lvtn_app/pages/sgddt/homepage.dart';
import 'package:lvtn_app/pages/sgddt/state_management.dart';
import 'package:lvtn_app/pages/sgddt/thisinh_management.dart';
import 'package:lvtn_app/pages/sgddt/update_score.dart';
import 'package:lvtn_app/pages/thcs/hocsinh_detail.dart';
import 'package:lvtn_app/pages/thcs/hocsinh_management.dart';
import 'package:lvtn_app/pages/thcs/homepage.dart';
import 'package:lvtn_app/pages/thpt/hocsinh_detail.dart';
import 'package:lvtn_app/pages/thpt/hocsinh_management.dart';
import 'package:lvtn_app/pages/thpt/homepage.dart';
import 'package:lvtn_app/pages/thpt/thisinh_management.dart';
import 'package:lvtn_app/pages/welcome_page.dart';
import 'package:lvtn_app/provider/auth_provider.dart';
import 'package:provider/provider.dart';

final GoRouter appRouter = GoRouter(
  initialLocation: '/welcome',
  routes: [
    // Trang welcome
    ShellRoute(
      builder: (context, state, child) => child,
      routes: [
        GoRoute(
          path: '/welcome',
          pageBuilder: (context, state) =>
              const NoTransitionPage(child: WelcomePage()),
        ),
      ],
    ),
    // ShellRoute cho các trang không đăng nhập
    ShellRoute(
      builder: (context, state, child) {
        final currentPath = state.uri.path;
        final pageTitle = PageTitle.of(currentPath);
        final noLoginRoutes = [
          '/',
          '/search-result',
          '/admission-info',
          '/information',
          '/login',
        ];
        return Scaffold(
          appBar: AppBar(title: Text(pageTitle)),
          body: child,
          bottomNavigationBar: BottomNavigationBar(
            type: BottomNavigationBarType.fixed,
            currentIndex: _calculateNoLoggedInIndex(state),
            onTap: (index) => context.go(noLoginRoutes[index]),
            items: const [
              BottomNavigationBarItem(
                icon: Icon(Icons.home),
                label: 'Trang chủ',
              ),
              BottomNavigationBarItem(
                icon: Icon(Icons.search),
                label: 'Tra cứu',
              ),
              BottomNavigationBarItem(
                icon: Icon(Icons.bar_chart),
                label: 'Chỉ tiêu',
              ),
              BottomNavigationBarItem(
                icon: Icon(Icons.article),
                label: 'Văn bản',
              ),
              BottomNavigationBarItem(
                icon: Icon(Icons.login),
                label: 'Đăng nhập',
              ),
            ],
          ),
        );
      },
      routes: [
        GoRoute(
          path: '/',
          pageBuilder: (context, state) =>
              const NoTransitionPage(child: Homepage()),
        ),
        GoRoute(
          path: '/search-result',
          pageBuilder: (context, state) =>
              const NoTransitionPage(child: SearchResult()),
        ),
        GoRoute(
          path: '/admission-info',
          pageBuilder: (context, state) =>
              const NoTransitionPage(child: AdmissionInfo()),
        ),
        GoRoute(
          path: '/information',
          pageBuilder: (context, state) =>
              const NoTransitionPage(child: Information()),
        ),
        GoRoute(
          path: '/login',
          pageBuilder: (context, state) =>
              const NoTransitionPage(child: LoginPage()),
        ),
      ],
    ),
    // ShellRoute cho nhóm trang admin
    ShellRoute(
      builder: (context, state, child) {
        final currentPath = state.uri.path;
        final pageTitle = PageTitle.of(currentPath);
        final adminRoutes = ['/admin/account', '/admin'];
        return Scaffold(
          appBar: AppBar(title: Text(pageTitle)),
          body: child,
          bottomNavigationBar: BottomNavigationBar(
            type: BottomNavigationBarType.fixed,
            currentIndex: _calculateAdminIndex(state),
            onTap: (index) => context.go(adminRoutes[index]),
            items: const [
              BottomNavigationBarItem(
                icon: Icon(Icons.manage_accounts),
                label: 'Quản lý tài khoản',
              ),
              BottomNavigationBarItem(
                icon: Icon(Icons.account_circle),
                label: 'Trang chủ',
              ),
            ],
          ),
        );
      },
      routes: [
        GoRoute(
          path: '/admin/account',
          redirect: (context, state) {
            final auth = Provider.of<AuthProvider>(context, listen: false);
            if (!auth.isLoggedIn) {
              return '/';
            }
            final user = auth.user!;
            final maLoaiTK = user['maLoaiTK'];
            if (maLoaiTK == null || maLoaiTK is! int || maLoaiTK != 0) {
              return '/';
            }
            return null;
          },
          pageBuilder: (context, state) =>
              const NoTransitionPage(child: AdminAccountManagement()),
        ),
        GoRoute(
          path: '/admin',
          redirect: (context, state) {
            final auth = Provider.of<AuthProvider>(context, listen: false);
            if (!auth.isLoggedIn) {
              return '/';
            }
            final user = auth.user!;
            final maLoaiTK = user['maLoaiTK'];
            if (maLoaiTK == null || maLoaiTK is! int || maLoaiTK != 0) {
              return '/';
            }
            return null;
          },
          pageBuilder: (context, state) =>
              const NoTransitionPage(child: AdminHomepage()),
        ),
      ],
    ),
    GoRoute(
      path: '/admin/change-password',
      redirect: (context, state) {
        final auth = Provider.of<AuthProvider>(context, listen: false);
        if (!auth.isLoggedIn) {
          return '/';
        }
        final user = auth.user!;
        final maLoaiTK = user['maLoaiTK'];
        if (maLoaiTK == null || maLoaiTK is! int || maLoaiTK != 0) {
          return '/';
        }
        return null;
      },
      pageBuilder: (context, state) {
        final currentPath = state.uri.path;
        final pageTitle = PageTitle.of(currentPath);
        return CustomTransitionPage<void>(
          key: state.pageKey,
          child: Scaffold(
            appBar: AppBar(title: Text(pageTitle)),
            body: const AdminChangePassword(),
          ),
          transitionsBuilder: (context, animation, secondaryAnimation, child) {
            const begin = Offset(1.0, 0.0);
            const end = Offset.zero;
            const curve = Curves.easeInOut;
            final tween = Tween(
              begin: begin,
              end: end,
            ).chain(CurveTween(curve: curve));

            return SlideTransition(
              position: animation.drive(tween),
              child: child,
            );
          },
          transitionDuration: const Duration(milliseconds: 300),
        );
      },
    ),
    // ShellRoute cho nhóm trang Học sinh
    ShellRoute(
      builder: (context, state, child) {
        final currentPath = state.uri.path;
        final pageTitle = PageTitle.of(currentPath);
        final hocsinhRoutes = ['/hocsinh/detail', '/hocsinh'];
        return Scaffold(
          appBar: AppBar(title: Text(pageTitle)),
          body: child,
          bottomNavigationBar: BottomNavigationBar(
            currentIndex: _calculateHocSinhIndex(state),
            onTap: (index) => context.go(hocsinhRoutes[index]),
            items: const [
              BottomNavigationBarItem(
                icon: Icon(Icons.contact_page),
                label: 'Thông tin học sinh',
              ),
              BottomNavigationBarItem(
                icon: Icon(Icons.account_circle),
                label: 'Trang chủ',
              ),
            ],
          ),
        );
      },
      routes: [
        GoRoute(
          path: '/hocsinh/detail',
          redirect: (context, state) {
            final auth = Provider.of<AuthProvider>(context, listen: false);
            if (!auth.isLoggedIn) {
              return '/';
            }
            final user = auth.user!;
            final maLoaiTK = user['maLoaiTK'];
            if (maLoaiTK == null || maLoaiTK is! int || maLoaiTK != 4) {
              return '/';
            }
            return null;
          },
          pageBuilder: (context, state) =>
              const NoTransitionPage(child: HocSinhDetail()),
        ),
        GoRoute(
          path: '/hocsinh',
          redirect: (context, state) {
            final auth = Provider.of<AuthProvider>(context, listen: false);
            if (!auth.isLoggedIn) {
              return '/';
            }
            final user = auth.user!;
            final maLoaiTK = user['maLoaiTK'];
            if (maLoaiTK == null || maLoaiTK is! int || maLoaiTK != 4) {
              return '/';
            }
            return null;
          },
          pageBuilder: (context, state) =>
              const NoTransitionPage(child: HocSinhHomepage()),
        ),
      ],
    ),
    GoRoute(
      path: '/hocsinh/change-password',
      redirect: (context, state) {
        final auth = Provider.of<AuthProvider>(context, listen: false);
        if (!auth.isLoggedIn) {
          return '/';
        }
        final user = auth.user!;
        final maLoaiTK = user['maLoaiTK'];
        if (maLoaiTK == null || maLoaiTK is! int || maLoaiTK != 4) {
          return '/';
        }
        return null;
      },
      pageBuilder: (context, state) {
        final currentPath = state.uri.path;
        final pageTitle = PageTitle.of(currentPath);
        return CustomTransitionPage<void>(
          key: state.pageKey,
          child: Scaffold(
            appBar: AppBar(title: Text(pageTitle)),
            body: const AdminChangePassword(),
          ),
          transitionsBuilder: (context, animation, secondaryAnimation, child) {
            const begin = Offset(1.0, 0.0);
            const end = Offset.zero;
            const curve = Curves.easeInOut;
            final tween = Tween(
              begin: begin,
              end: end,
            ).chain(CurveTween(curve: curve));

            return SlideTransition(
              position: animation.drive(tween),
              child: child,
            );
          },
          transitionDuration: const Duration(milliseconds: 300),
        );
      },
    ),
    // ShellRoute cho nhóm trang SGDĐT (HĐTS)
    ShellRoute(
      builder: (context, state, child) {
        final currentPath = state.uri.path;
        final pageTitle = PageTitle.of(currentPath);
        final hdtsRoutes = ['/sgddt/thisinh', '/sgddt/trangthai', '/sgddt'];
        return Scaffold(
          appBar: AppBar(title: Text(pageTitle)),
          body: child,
          bottomNavigationBar: BottomNavigationBar(
            currentIndex: _calculateSGDDTIndex(state),
            onTap: (index) => context.go(hdtsRoutes[index]),
            items: const [
              BottomNavigationBarItem(
                icon: Icon(Icons.manage_accounts),
                label: 'Quản lý thí sinh',
              ),
              BottomNavigationBarItem(
                icon: Icon(Icons.admin_panel_settings),
                label: 'Quản lý trạng thái',
              ),
              BottomNavigationBarItem(
                icon: Icon(Icons.account_circle),
                label: 'Trang chủ',
              ),
            ],
          ),
        );
      },
      routes: [
        GoRoute(
          path: '/sgddt/thisinh',
          redirect: (context, state) {
            final auth = Provider.of<AuthProvider>(context, listen: false);
            if (!auth.isLoggedIn) {
              return '/';
            }
            final user = auth.user!;
            final maLoaiTK = user['maLoaiTK'];
            if (maLoaiTK == null || maLoaiTK is! int || maLoaiTK != 1) {
              return '/';
            }
            return null;
          },
          pageBuilder: (context, state) =>
              const NoTransitionPage(child: HDTSThiSinhManagement()),
        ),
        GoRoute(
          path: '/sgddt/trangthai',
          redirect: (context, state) {
            final auth = Provider.of<AuthProvider>(context, listen: false);
            if (!auth.isLoggedIn) {
              return '/';
            }
            final user = auth.user!;
            final maLoaiTK = user['maLoaiTK'];
            if (maLoaiTK == null || maLoaiTK is! int || maLoaiTK != 1) {
              return '/';
            }
            return null;
          },
          pageBuilder: (context, state) =>
              const NoTransitionPage(child: HDTSStateManagement()),
        ),
        GoRoute(
          path: '/sgddt',
          redirect: (context, state) {
            final auth = Provider.of<AuthProvider>(context, listen: false);
            if (!auth.isLoggedIn) {
              return '/';
            }
            final user = auth.user!;
            final maLoaiTK = user['maLoaiTK'];
            if (maLoaiTK == null || maLoaiTK is! int || maLoaiTK != 1) {
              return '/';
            }
            return null;
          },
          pageBuilder: (context, state) =>
              const NoTransitionPage(child: HDTSHomepage()),
        ),
      ],
    ),
    GoRoute(
      path: '/sgddt/change-password',
      redirect: (context, state) {
        final auth = Provider.of<AuthProvider>(context, listen: false);
        if (!auth.isLoggedIn) {
          return '/';
        }
        final user = auth.user!;
        final maLoaiTK = user['maLoaiTK'];
        if (maLoaiTK == null || maLoaiTK is! int || maLoaiTK != 1) {
          return '/';
        }
        return null;
      },
      pageBuilder: (context, state) {
        final currentPath = state.uri.path;
        final pageTitle = PageTitle.of(currentPath);
        return CustomTransitionPage<void>(
          key: state.pageKey,
          child: Scaffold(
            appBar: AppBar(title: Text(pageTitle)),
            body: const AdminChangePassword(),
          ),
          transitionsBuilder: (context, animation, secondaryAnimation, child) {
            const begin = Offset(1.0, 0.0);
            const end = Offset.zero;
            const curve = Curves.easeInOut;
            final tween = Tween(
              begin: begin,
              end: end,
            ).chain(CurveTween(curve: curve));

            return SlideTransition(
              position: animation.drive(tween),
              child: child,
            );
          },
          transitionDuration: const Duration(milliseconds: 300),
        );
      },
    ),
    GoRoute(
      path: '/sgddt/thisinh/update-score/:sbd',
      redirect: (context, state) {
        final auth = Provider.of<AuthProvider>(context, listen: false);
        if (!auth.isLoggedIn) {
          return '/';
        }
        final user = auth.user!;
        final maLoaiTK = user['maLoaiTK'];
        if (maLoaiTK == null || maLoaiTK is! int || maLoaiTK != 1) {
          return '/';
        }
        return null;
      },
      pageBuilder: (context, state) {
        final sbd = state.pathParameters['sbd'] as String;
        final currentPath = state.uri.path;
        final pageTitle = PageTitle.of(
          currentPath.startsWith('/sgddt/thisinh/update-score')
              ? '/sgddt/thisinh/update-score'
              : currentPath,
        );
        return CustomTransitionPage<void>(
          key: state.pageKey,
          child: Scaffold(
            appBar: AppBar(title: Text(pageTitle)),
            body: HDTSUpdateScore(sbd: sbd),
          ),
          transitionsBuilder: (context, animation, secondaryAnimation, child) {
            const begin = Offset(1.0, 0.0);
            const end = Offset.zero;
            const curve = Curves.easeInOut;
            final tween = Tween(
              begin: begin,
              end: end,
            ).chain(CurveTween(curve: curve));

            return SlideTransition(
              position: animation.drive(tween),
              child: child,
            );
          },
          transitionDuration: const Duration(milliseconds: 300),
        );
      },
    ),
    // ShellRoute cho nhóm trang THCS
    ShellRoute(
      builder: (context, state, child) {
        final currentPath = state.uri.path;
        final pageTitle = PageTitle.of(currentPath);
        final thcsRoutes = ['/thcs/hocsinh', '/thcs'];
        return Scaffold(
          appBar: AppBar(title: Text(pageTitle)),
          body: child,
          bottomNavigationBar: BottomNavigationBar(
            currentIndex: _calculateTHCSIndex(state),
            onTap: (index) => context.go(thcsRoutes[index]),
            items: const [
              BottomNavigationBarItem(
                icon: Icon(Icons.manage_accounts),
                label: 'Quản lý học sinh',
              ),
              BottomNavigationBarItem(
                icon: Icon(Icons.account_circle),
                label: 'Trang chủ',
              ),
            ],
          ),
        );
      },
      routes: [
        GoRoute(
          path: '/thcs/hocsinh',
          redirect: (context, state) {
            final auth = Provider.of<AuthProvider>(context, listen: false);
            if (!auth.isLoggedIn) {
              return '/';
            }
            final user = auth.user!;
            final maLoaiTK = user['maLoaiTK'];
            if (maLoaiTK == null || maLoaiTK is! int || maLoaiTK != 2) {
              return '/';
            }
            return null;
          },
          pageBuilder: (context, state) =>
              const NoTransitionPage(child: THCSHocSinhManagement()),
        ),
        GoRoute(
          path: '/thcs',
          redirect: (context, state) {
            final auth = Provider.of<AuthProvider>(context, listen: false);
            if (!auth.isLoggedIn) {
              return '/';
            }
            final user = auth.user!;
            final maLoaiTK = user['maLoaiTK'];
            if (maLoaiTK == null || maLoaiTK is! int || maLoaiTK != 2) {
              return '/';
            }
            return null;
          },
          pageBuilder: (context, state) =>
              const NoTransitionPage(child: THCSHomepage()),
        ),
      ],
    ),
    GoRoute(
      path: '/thcs/change-password',
      redirect: (context, state) {
        final auth = Provider.of<AuthProvider>(context, listen: false);
        if (!auth.isLoggedIn) {
          return '/';
        }
        final user = auth.user!;
        final maLoaiTK = user['maLoaiTK'];
        if (maLoaiTK == null || maLoaiTK is! int || maLoaiTK != 2) {
          return '/';
        }
        return null;
      },
      pageBuilder: (context, state) {
        final currentPath = state.uri.path;
        final pageTitle = PageTitle.of(currentPath);
        return CustomTransitionPage<void>(
          key: state.pageKey,
          child: Scaffold(
            appBar: AppBar(title: Text(pageTitle)),
            body: const AdminChangePassword(),
          ),
          transitionsBuilder: (context, animation, secondaryAnimation, child) {
            const begin = Offset(1.0, 0.0);
            const end = Offset.zero;
            const curve = Curves.easeInOut;
            final tween = Tween(
              begin: begin,
              end: end,
            ).chain(CurveTween(curve: curve));

            return SlideTransition(
              position: animation.drive(tween),
              child: child,
            );
          },
          transitionDuration: const Duration(milliseconds: 300),
        );
      },
    ),
    GoRoute(
      path: '/thcs/hocsinh/detail/:maHS',
      redirect: (context, state) {
        final auth = Provider.of<AuthProvider>(context, listen: false);
        if (!auth.isLoggedIn) {
          return '/';
        }
        final user = auth.user!;
        final maLoaiTK = user['maLoaiTK'];
        if (maLoaiTK == null || maLoaiTK is! int || maLoaiTK != 2) {
          return '/';
        }
        return null;
      },
      pageBuilder: (context, state) {
        final maHS = state.pathParameters['maHS'] as String;
        final currentPath = state.uri.path;
        final pageTitle = PageTitle.of(
          currentPath.startsWith('/thcs/hocsinh/detail')
              ? '/thcs/hocsinh/detail'
              : currentPath,
        );
        return CustomTransitionPage<void>(
          key: state.pageKey,
          child: Scaffold(
            appBar: AppBar(title: Text(pageTitle)),
            body: THCSHocSinhDetail(maHS: maHS),
          ),
          transitionsBuilder: (context, animation, secondaryAnimation, child) {
            const begin = Offset(1.0, 0.0);
            const end = Offset.zero;
            const curve = Curves.easeInOut;
            final tween = Tween(
              begin: begin,
              end: end,
            ).chain(CurveTween(curve: curve));

            return SlideTransition(
              position: animation.drive(tween),
              child: child,
            );
          },
          transitionDuration: const Duration(milliseconds: 300),
        );
      },
    ),
    // ShellRoute cho nhóm trang THPT
    ShellRoute(
      builder: (context, state, child) {
        final currentPath = state.uri.path;
        final pageTitle = PageTitle.of(currentPath);
        final thptRoutes = ['/thpt/hocsinh', '/thpt/thisinh', '/thpt'];
        return Scaffold(
          appBar: AppBar(title: Text(pageTitle)),
          body: child,
          bottomNavigationBar: BottomNavigationBar(
            currentIndex: _calculateTHPTIndex(state),
            onTap: (index) => context.go(thptRoutes[index]),
            items: const [
              BottomNavigationBarItem(
                icon: Icon(Icons.manage_accounts),
                label: 'Quản lý học sinh',
              ),
              BottomNavigationBarItem(
                icon: Icon(Icons.manage_accounts),
                label: 'Quản lý thí sinh',
              ),
              BottomNavigationBarItem(
                icon: Icon(Icons.home),
                label: 'Trang chủ',
              ),
            ],
          ),
        );
      },
      routes: [
        GoRoute(
          path: '/thpt/hocsinh',
          redirect: (context, state) {
            final auth = Provider.of<AuthProvider>(context, listen: false);
            if (!auth.isLoggedIn) {
              return '/';
            }
            final user = auth.user!;
            final maLoaiTK = user['maLoaiTK'];
            if (maLoaiTK == null || maLoaiTK is! int || maLoaiTK != 3) {
              return '/';
            }
            return null;
          },
          pageBuilder: (context, state) =>
              const NoTransitionPage(child: THPTHocSinhManagement()),
        ),
        GoRoute(
          path: '/thpt/thisinh',
          redirect: (context, state) {
            final auth = Provider.of<AuthProvider>(context, listen: false);
            if (!auth.isLoggedIn) {
              return '/';
            }
            final user = auth.user!;
            final maLoaiTK = user['maLoaiTK'];
            if (maLoaiTK == null || maLoaiTK is! int || maLoaiTK != 3) {
              return '/';
            }
            return null;
          },
          pageBuilder: (context, state) =>
              const NoTransitionPage(child: THPTThiSinhManagement()),
        ),
        GoRoute(
          path: '/thpt',
          redirect: (context, state) {
            final auth = Provider.of<AuthProvider>(context, listen: false);
            if (!auth.isLoggedIn) {
              return '/';
            }
            final user = auth.user!;
            final maLoaiTK = user['maLoaiTK'];
            if (maLoaiTK == null || maLoaiTK is! int || maLoaiTK != 3) {
              return '/';
            }
            return null;
          },
          pageBuilder: (context, state) =>
              const NoTransitionPage(child: THPTHomepage()),
        ),
      ],
    ),
    GoRoute(
      path: '/thpt/change-password',
      redirect: (context, state) {
        final auth = Provider.of<AuthProvider>(context, listen: false);
        if (!auth.isLoggedIn) {
          return '/';
        }
        final user = auth.user!;
        final maLoaiTK = user['maLoaiTK'];
        if (maLoaiTK == null || maLoaiTK is! int || maLoaiTK != 3) {
          return '/';
        }
        return null;
      },
      pageBuilder: (context, state) {
        final currentPath = state.uri.path;
        final pageTitle = PageTitle.of(currentPath);
        return CustomTransitionPage<void>(
          key: state.pageKey,
          child: Scaffold(
            appBar: AppBar(title: Text(pageTitle)),
            body: const AdminChangePassword(),
          ),
          transitionsBuilder: (context, animation, secondaryAnimation, child) {
            const begin = Offset(1.0, 0.0);
            const end = Offset.zero;
            const curve = Curves.easeInOut;
            final tween = Tween(
              begin: begin,
              end: end,
            ).chain(CurveTween(curve: curve));

            return SlideTransition(
              position: animation.drive(tween),
              child: child,
            );
          },
          transitionDuration: const Duration(milliseconds: 300),
        );
      },
    ),
    GoRoute(
      path: '/thpt/hocsinh/detail/:maHS',
      redirect: (context, state) {
        final auth = Provider.of<AuthProvider>(context, listen: false);
        if (!auth.isLoggedIn) {
          return '/';
        }
        final user = auth.user!;
        final maLoaiTK = user['maLoaiTK'];
        if (maLoaiTK == null || maLoaiTK is! int || maLoaiTK != 3) {
          return '/';
        }
        return null;
      },
      pageBuilder: (context, state) {
        final maHS = state.pathParameters['maHS'] as String;
        final currentPath = state.uri.path;
        final pageTitle = PageTitle.of(
          currentPath.startsWith('/thpt/hocsinh/detail')
              ? '/thpt/hocsinh/detail'
              : currentPath,
        );
        return CustomTransitionPage<void>(
          key: state.pageKey,
          child: Scaffold(
            appBar: AppBar(title: Text(pageTitle)),
            body: THPTHocSinhDetail(maHS: maHS),
          ),
          transitionsBuilder: (context, animation, secondaryAnimation, child) {
            const begin = Offset(1.0, 0.0);
            const end = Offset.zero;
            const curve = Curves.easeInOut;
            final tween = Tween(
              begin: begin,
              end: end,
            ).chain(CurveTween(curve: curve));

            return SlideTransition(
              position: animation.drive(tween),
              child: child,
            );
          },
          transitionDuration: const Duration(milliseconds: 300),
        );
      },
    ),
  ],
);

int _calculateNoLoggedInIndex(GoRouterState state) {
  final path = state.uri.path;
  switch (path) {
    case '/':
      return 0;
    case '/search-result':
      return 1;
    case '/admission-info':
      return 2;
    case '/information':
      return 3;
    case '/login':
      return 4;
    default:
      return 0;
  }
}

int _calculateAdminIndex(GoRouterState state) {
  final path = state.uri.path;
  switch (path) {
    case '/admin/account':
      return 0;
    case '/admin':
      return 1;
    default:
      return 1;
  }
}

int _calculateHocSinhIndex(GoRouterState state) {
  final path = state.uri.path;
  switch (path) {
    case '/hocsinh/detail':
      return 0;
    case '/hocsinh':
      return 1;
    default:
      return 1;
  }
}

int _calculateSGDDTIndex(GoRouterState state) {
  final path = state.uri.path;
  switch (path) {
    case '/sgddt/thisinh':
      return 0;
    case '/sgddt/trangthai':
      return 1;
    case '/sgddt':
      return 2;
    default:
      return 2;
  }
}

int _calculateTHCSIndex(GoRouterState state) {
  final path = state.uri.path;
  switch (path) {
    case '/thcs/hocsinh':
      return 0;
    case '/thcs':
      return 1;
    default:
      return 1;
  }
}

int _calculateTHPTIndex(GoRouterState state) {
  final path = state.uri.path;
  switch (path) {
    case '/thpt/hocsinh':
      return 0;
    case '/thpt/thisinh':
      return 1;
    case '/thpt':
      return 2;
    default:
      return 2;
  }
}
