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

function readBoards(page) {
    return axiosInstance.get('/boards?page=' + (page || 1));
}

function readBoard(boardId) {
    return axiosInstance.get('/boards/' + boardId);
}

function updateBoard(boardId) {
    return axiosInstance.get('/boards/' + boardId);
}

function deleteBoard(boardId) {
    return axiosInstance.get('/boards/' + boardId);
}

function createComment(boardId, dto) {
    return axiosInstance.post(`boards/${ boardId }/comments`, dto);
}

function updateComment(boardId, dto) {
    return axiosInstance.get('/boards/' + boardId, dto);
}

function deleteComment(boardId, dto) {
    return axiosInstance.get('/boards/' + boardId, dto);
}

function uploadImage(image) {
    return axiosInstance.post('/images', image);
}

function createSchedule(scheduleData) {
    return axiosInstance.post('/schedules', scheduleData)
}

function readSchedule(scheduleId) {
    return axiosInstance.get('/schedules/write/' + scheduleId)
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
function readAllMySchedules() {
    return axiosInstance.get('/users/me/schedules')
}

function readMySchedule(scheduleId) {
    return axiosInstance.get(`/users/me/schedules/${scheduleId}`)
}

function readAllSchedules() {
    return axiosInstance.get('/schedules')
}

function readBoardSchedule(scheduleId) {
    return axiosInstance.get(`/schedules/${scheduleId}`)
}

function updateSchedule(scheduleId, scheduleData) {
    return axiosInstance.put(`/schedules/${scheduleId}`, scheduleData)
}

function updateScheduleItems(scheduleId) {
    return axiosInstance.put(`/schedules/${scheduleId}/schedule-items`)
}


export {
    registerUser, loginUser, readUserInfo, updateUserInfo, findUser,
    createBoard, readBoards, uploadImage,
    createSchedule, createScheduleItems, readSchedule, readLikedItemBySido, createRouteList, readMySchedule, readAllSchedules, readAllMySchedules, readBoardSchedule, updateSchedule, updateScheduleItems,
    readInvitations, acceptInvitation, rejectInvitation, findInvitationList,inviteUserToSchedule,
    bookmarkItem
};

