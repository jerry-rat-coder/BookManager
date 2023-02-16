import request from '@/utils/request'
// 添加用户回收
export function addUserGarb(data) {
    return request({
        url: '/userGarb/addUserGarb',
        method: 'post',
        data
    })
}