import axiosInstance from '@/store/interceptor.js';


function registerUser(userData) {
    return axiosInstance.post('users/register', userData);
}

function loginUser(userData) {
  return axiosInstance.post('/users/login', userData);
}

function updateUserInfo(userData) {
    const headers = {
        'Content-Type': 'multipart/form-data'
    };
    return axiosInstance.put('/users/me', userData, { headers });
}

function createBoard(boardData) {
    return axiosInstance.post('/boards', boardData);
}

function readBoards() {
    return axiosInstance.get('/boards');
}

function uploadImage(image) {
    return axiosInstance.post('/images', image);
}

function createSchedule(scheduleData) {
    return axiosInstance.post('/schedules', scheduleData)
}

function readSchedule(scheduleId) {
    return axiosInstance.get('/schedules/' + scheduleId)
}

function readLikedItemBySido(sido, page) {
    return axiosInstance.get(`/users/me/liked-items/${sido}?page=${page}`)
}

function createRouteList(scheduleId, tourList) {
    return axiosInstance.post('/schedules/' + scheduleId + '/schedule-items/route', tourList)
}

function readInvitations(){
    return axiosInstance.get('/schedules/invited-users');
}
function acceptInvitation(scheduleId, matesId) {
    return axiosInstance.post(`/schedules/invited-users/${scheduleId}/acceptance/${matesId}`);
}
function rejectInvitation(scheduleId, matesId) {
    return axiosInstance.post(`/schedules/invited-users/${scheduleId}/rejection/${matesId}`);
}

function createScheduleItems(scheduleId) {
    return axiosInstance.post(`/schedules/${scheduleId}/schedule-items`)
}

function findUser(keyword) {
    return axiosInstance.get(`/users?q=${keyword}`)
}

function likeUser(toUserId) {
    return axiosInstance.post(`/users/me/liked-user/${toUserId}`)
}

// 현재 회원의 모든 정보 조회
function readUserInfo(userData) {
    return axiosInstance.get('/users/me')
}

// 다른 유저 정보 조회
// function readUserProfile(email){
//     return axiosInstance.get('/users/${email}')
// }

function findInvitationList(scheduleId,keyword) {
    // return axiosInstance.get(`/schedules/invited-users/${scheduleId}?q=${keyword}`)
    return axiosInstance.get(`/schedules/invited-users/${scheduleId}?q=${keyword}`)
}

function inviteUserToSchedule(scheduleId,invitedUsername) {
    return axiosInstance.post(`/schedules/invited-users/${scheduleId}?q=${invitedUsername}`)
}
function bookmarkItem(itemId) {
    return axiosInstance.post(`item-list/add/${itemId}`)
}

export {
    registerUser, loginUser, readUserInfo, updateUserInfo, findUser, likeUser,
    createBoard, readBoards, uploadImage,
    createSchedule, createScheduleItems, readSchedule, readLikedItemBySido, createRouteList,
    readInvitations, acceptInvitation, rejectInvitation, findInvitationList,inviteUserToSchedule,
    bookmarkItem
};

