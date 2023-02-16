import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'
export const constantRoutes = [{
        path: '/login',
        component: () =>
            import ('@/views/login/index'),
        hidden: true
    },

    {
        path: '/register',
        component: () =>
            import ('@/views/register/index'),
        hidden: true
    },

    {
        path: '/404',
        component: () =>
            import ('@/views/404'),
        hidden: true
    },

    {
        path: '/',
        component: Layout,
        redirect: '/dashboard',
        children: [{
            path: 'dashboard',
            name: 'Dashboard',
            component: () =>
                import ('@/views/dashboard/index'),
            meta: { title: '首页', icon: 'dashboard' }
        }]
    }
]

/**
 * asyncRoutes
 * the routes that need to be dynamically loaded based on user roles
 */
export const asyncRoutes = [{
        path: '/person',
        component: Layout,
        redirect: '/person',
        children: [{
            path: 'person',
            name: 'Person',
            component: () =>
                import ('@/views/person/index'),
            meta: {
                title: '个人信息',
                icon: 'user',
                roles: ['reader']
            }
        }]
    },

    {
        path: '/bookmanage',
        name: 'Bookmanage',
        component: Layout,
        redirect: '/bookmanage/bookinfo',
        alwaysShow: true,
        meta: {
            title: '图书管理',
            icon: 'book'
        },
        children: [{
                path: 'bookinfo',
                name: 'Bookinfo',
                component: () =>
                    import ('@/views/bookinfo/index'),
                meta: {
                    title: '图书信息管理',
                    icon: 'form',
                    roles: ['admin', 'reader'],
                    noCache: true
                }
            },
            {
                path: 'booktype',
                name: 'Booktype',
                component: () =>
                    import ('@/views/booktype/index'),
                meta: {
                    title: '图书类型管理',
                    icon: 'example',
                    roles: ['admin'],
                    noCache: true
                }
            },
            {
                path: 'borrow',
                name: 'Borrow',
                component: () =>
                    import ('@/views/borrow/index'),
                meta: {
                    title: '借阅信息管理',
                    icon: 'borrow',
                    roles: ['admin', 'reader'],
                    noCache: true
                }
            },
            {
                path: 'bookgarb',
                name: 'Bookgarb',
                component: () =>
                    import ('@/views/bookgarb/index'),
                meta: {
                    title: '图书回收站管理',
                    icon: 'example',
                    roles: ['admin'],
                    noCache: true
                }
            },
        ]
    },

    {
        path: '/other',
        name: 'Other',
        component: Layout,
        redirect: '/other/user',
        alwaysShow: true,
        meta: {
            title: '其他管理',
            icon: 'other'
        },
        children: [{
                path: 'user',
                name: 'User',
                component: () =>
                    import ('@/views/user/index'),
                meta: {
                    title: '用户管理',
                    icon: 'user',
                    roles: ['admin'],
                    noCache: true
                }
            },
            {
                path: 'password',
                name: 'Password',
                component: () =>
                    import ('@/views/password/index'),
                meta: {
                    title: '修改密码',
                    icon: 'password',
                    roles: ['admin', 'reader'],
                    noCache: true
                }
            },
            {
                path: 'adminRecord',
                name: 'Password',
                component: () =>
                    import ('@/views/adminOp/adminRecord'),
                meta: {
                    title: '管理员操作日志',
                    icon: 'form',
                    roles: ['admin'],
                    noCache: true
                }
            }
        ]
    },

    // 404 page must be placed at the end !!!
    { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
    // mode: 'history', // require service support
    scrollBehavior: () => ({ y: 0 }),
    routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
    const newRouter = createRouter()
    router.matcher = newRouter.matcher // reset router
}

export default router