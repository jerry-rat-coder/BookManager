const getters = {
    sidebar: state => state.app.sidebar,
    device: state => state.app.device,
    token: state => state.user.token,
    id: state => state.user.id,
    avatar: state => state.user.avatar,
    name: state => state.user.name,
    email: state => state.user.email,
    phone: state => state.user.phone,
    nickname: state => state.user.nickname,
    roles: state => state.user.roles,
    countvio: state => state.user.countvio,
    permission_routes: state => state.permission.routes
}
export default getters