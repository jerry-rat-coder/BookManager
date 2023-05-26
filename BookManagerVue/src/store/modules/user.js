import { login, logout, getInfo } from '@/api/user'
import { getToken, setToken, removeToken } from '@/utils/auth'
import { resetRouter } from '@/router'
import { Vue } from 'vue'

const getDefaultState = () => {
    return {
        token: getToken(),
        id: 0,
        name: '',
        avatar: '',
        roles: [],
        email: '',
        phone: '',
        nickname: ''
    }
}

const state = getDefaultState()

const mutations = {
    RESET_STATE: (state) => {
        Object.assign(state, getDefaultState())
    },
    SET_TOKEN: (state, token) => {
        state.token = ''
        state.token = token
            // Vue.set(state, 'token', token)
    },
    SET_ID: (state, id) => {
        state.id = id
            // Vue.set(state, 'id', id)
    },
    SET_NAME: (state, name) => {
        state.name = ''
        state.name = name
            // Vue.set(state, 'name', name)
    },
    SET_AVATAR: (state, avatarurl) => {
        state.avatar = ''
        state.avatar = avatarurl
            // Vue.set(state, 'avatar', avatarurl)
    },
    SET_ROLES: (state, roles) => {
        state.roles = ''
        state.roles = roles
            // Vue.set(state, 'roles', roles)
    },
    SET_PHONE: (state, phone) => {
        state.phone = ''
        state.phone = phone
            // Vue.set(state, 'phone', phone)
    },
    SET_EMAIL: (state, email) => {
        state.email = ''
        state.email = email
            // Vue.set(state, 'email', email)
    },
    SET_NICKNAME: (state, nickname) => {
        state.nickname = ''
        state.nickname = nickname
            // Vue.set(state, 'nickname', nickname)
    },
    SET_CNT: (state, countvio) => {
        state.countvio = ''
        state.countvio = countvio
            // Vue.set(state, 'nickname', nickname)
    }
}

const actions = {
    // user login
    login({ commit }, userInfo) {
        const { username, password, isadmin } = userInfo
        return new Promise((resolve, reject) => {
            login({ username: username.trim(), userpassword: password, isadmin: isadmin }).then(response => {
                const { status, message, data } = response

                if (status !== 200) {
                    reject(message)
                }
                console.log(message)
                const { token, username, avatar, nickname, email, phone } = data
                commit('SET_TOKEN', data.token)
                    // commit('SET_ROLES', data.roles)
                commit('SET_NAME', username)
                commit('SET_AVATAR', avatar)
                commit('SET_NICKNAME', nickname)
                commit('SET_EMAIL', email)
                commit('SET_PHONE', phone)
                setToken(data.token)
                resolve()
            }).catch(error => {
                reject(error)
            })
        })
    },
    //admin login
    logins({ commit }, userInfo) {
        const { username, password, isadmin } = userInfo
        return new Promise((resolve, reject) => {
            login({ username: username.trim(), userpassword: password, isadmin: 0 }).then(response => {
                const { status, message, data } = response

                if (status !== 200) {
                    reject(message)
                }
                console.log(message)
                const { token, username, avatar, nickname, email, phone } = data
                commit('SET_TOKEN', data.token)
                commit('SET_NAME', username)
                commit('SET_AVATAR', avatar)
                commit('SET_NICKNAME', nickname)
                commit('SET_EMAIL', email)
                commit('SET_PHONE', phone)
                setToken(data.token)
                resolve()
            }).catch(error => {
                reject(error)
            })
        })
    },

    // get user info
    getInfo({ commit, state }) {
        return new Promise((resolve, reject) => {
            getInfo(state.token).then(response => {
                const { data } = response

                if (!data) {
                    reject('验证失败，请重新登录')
                }

                if (data.isadmin === 1) {
                    data['roles'] = ['admin']
                    data['avatar'] = 'https://cdn.acwing.com/media/user/profile/photo/60364_lg_786b0c4c45.jpg'
                } else {
                    data['roles'] = ['reader']
                    data['avatar'] = data.avatarurl
                }


                const { userid, roles, username, avatar, email, phone, nickname, countvio } = data

                // roles must be a non-empty array
                if (!roles || roles.length <= 0) {
                    reject('getInfo: roles 必须是非空数组!')
                }

                commit('SET_ID', userid)
                commit('SET_ROLES', roles)
                commit('SET_NAME', username)
                commit('SET_AVATAR', avatar)
                commit('SET_NICKNAME', nickname)
                commit('SET_EMAIL', email)
                commit('SET_PHONE', phone)
                commit('SET_CNT', countvio)
                resolve(data)
            }).catch(error => {
                reject(error)
            })
        })
    },

    // user logout
    logout({ commit, state }) {
        return new Promise((resolve, reject) => {
            logout(state.token).then(() => {
                removeToken() // must remove  token  first
                resetRouter()
                commit('RESET_STATE')
                resolve()
            }).catch(error => {
                reject(error)
            })
        })
    },

    // remove token
    resetToken({ commit }) {
        return new Promise(resolve => {
            removeToken() // must remove  token  first
            commit('RESET_STATE')
            resolve()
        })
    }
}

export default {
    namespaced: true,
    state,
    mutations,
    actions
}