import axiosInstance from '@/store/interceptor.js';


function loginUser(userData) {
  return axiosInstance.post('/users/login', userData);
}

function updateUserInfo(userData) {
    const headers = {
        'Content-Type': 'multipart/form-data'
    };
    return axiosInstance.put('/users/me', userData, { headers });
}

function deleteUser() {
    return axiosInstance.put('/users/me/delete');
}

function checkPassword(userData) {
    return axiosInstance.post('/users/check/verify-password',userData);
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

function likeUser(toUserId) {
    return axiosInstance.post(`/users/me/liked-user/${toUserId}`)
}

function unLikeUser(toUserId) {
    return axiosInstance.put(`/users/me/liked-user/${toUserId}`)
}

function readUserLikedByMe() {
    return axiosInstance.get(`/users/me/liked-user/to`)
}

function readUserLikedMe() {
    return axiosInstance.get(`/users/me/liked-user/from`)
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

function updateScheduleDisplay(scheduleId) {
    return axiosInstance.put(`/schedules/${scheduleId}/display`)
}


function itemReview(itemId, reviewData) {
    return axiosInstance.post(`/item-detail/read/${itemId}`, reviewData)
}
function updateReview(itemId, itemReviewId, content) {
    const apiUrl = `/item-detail/read/${itemId}/reviews/${itemReviewId}`;
    return axiosInstance.put(apiUrl, { content });
}

function deleteReview(itemId, itemReviewId) {
    const apiUrl = `/item-detail/read/${itemId}/reviews/${itemReviewId}`;
    return axiosInstance.delete(apiUrl);
}

export {
    loginUser, readUserInfo, updateUserInfo, findUser, checkPassword, deleteUser,
    likeUser, readUserLikedByMe, readUserLikedMe, unLikeUser,
    createBoard, readBoards, updateBoard, deleteBoard, uploadImage, readBoard,
    createComment,updateComment, deleteComment,
    createSchedule, createScheduleItems, readSchedule,
    createRouteList, readMySchedule, readAllSchedules, readAllMySchedules, readBoardSchedule,
    updateScheduleDisplay, updateSchedule, updateScheduleItems,
    readInvitations, acceptInvitation, rejectInvitation, findInvitationList, inviteUserToSchedule,
    bookmarkItem, itemReview, updateReview, deleteReview, readLikedItemBySido,
};

