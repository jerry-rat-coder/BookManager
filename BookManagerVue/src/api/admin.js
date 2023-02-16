import request from '@/utils/request'

// 获取日志数量
export function getCount() {
    return request({
        url: '/admin/getCount',
        method: 'get'
    })
}

// 查询所有日志
export function queryAdmins() {
    return request({
        url: '/admin/queryAdmins',
        method: 'get'
    })
}

// 分页查询日志
export function queryAdminsByPage(params) {
    return request({
        url: '/admin/queryAdminsByPage',
        method: 'get',
        params
    })
}

// 添加日志
export function addAdmin(id, content, note) {
    // console.log(id)
    // console.log(content)
    return request({
        url: '/admin/addadmin',
        method: 'post',
        params: {
            id,
            content,
            note
        }
    })
}

// 删除日志
export function deleteAdmin(data) {
    console.log(data)
    return request({
        url: '/admin/deleteAdmin',
        method: 'delete',
        data
    })
}

//  删除一些日志
export function deleteAdmins(data) {
    return request({
        url: '/admin/deleteAdmins',
        method: 'delete',
        data
    })
}
export function ech() {
    // console.log(1111)
    return request({
        url: '/echarts/example',
        method: 'get'
    })
}
export function zhu() {
    return request({
        url: '/echarts/members',
        method: 'get'
    })
}
// //  更新图书类型
// export function updateAdmin(data) {
//     return request({
//         url: '/admin/updateadmin',
//         method: 'put',
//         data
//     })
// }