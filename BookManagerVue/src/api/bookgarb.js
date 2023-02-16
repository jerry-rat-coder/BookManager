import request from '@/utils/request'

// 获取图书数量
export function getCount() {
    return request({
        url: '/bookGarb/getCount',
        method: 'get'
    })
}

// 查询所有图书信息
export function queryBookInfos() {
    return request({
        url: '/bookGarb/queryBookInfos',
        method: 'get'
    })
}

// 分页查询图书信息
export function queryBookInfosByPage(params) {
    return request({
        url: '/bookGarb/queryBookInfosByPage',
        method: 'get',
        params
    })
}

// 添加图书信息
export function addBookInfo(data) {
    return request({
        url: '/bookGarb/addBookInfo',
        method: 'post',
        data
    })
}

// 删除图书信息
export function deleteBookInfo(data) {
    return request({
        url: '/bookGarb/deleteBookInfo',
        method: 'delete',
        data
    })
}

//  删除一些图书信息
export function deleteBookInfos(data) {
    return request({
        url: '/bookGarb/deleteBookInfos',
        method: 'delete',
        data
    })
}

//  更新图书信息
export function updateBookInfo(data) {
    return request({
        url: '/bookGarb/updateBookInfo',
        method: 'put',
        data
    })
}

export function recoverBookInfo(data) {
    return request({
        url: '/bookGarb/recoverBookInfo',
        method: 'post',
        data
    })
}
export function recoverBookInfos(data) {
    return request({
        url: '/bookGarb/recoverBookInfos',
        method: 'post',
        data
    })
}