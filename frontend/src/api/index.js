import axiosInstance from '@/store/interceptor.js';


function registerUser(userData) {
    return axiosInstance.post('users/register', userData);
}

function loginUser(userData) {
  return axiosInstance.post('/users/login', userData);
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

<<<<<<< HEAD
function findUser(keyword) {
    return axiosInstance.get(`/users?q=${keyword}`)
}

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
function readAllSchedules() {
=======
function readAllMySchedules() {
>>>>>>> 6677a0e (refactor : 커뮤니티게시판 스케줄 목록, 세부 일정 출력, my page의 기타 페이지 html, css)
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


export {
    registerUser, loginUser, createBoard, readBoards, createSchedule, readSchedule, readLikedItemBySido, createRouteList,
<<<<<<< HEAD
<<<<<<< HEAD
    readInvitations, acceptInvitation, rejectInvitation, createScheduleItems, findUser, findInvitationList,inviteUserToSchedule,
    bookmarkItem, readAllSchedules
=======
    readInvitations, acceptInvitation, rejectInvitation, createScheduleItems, readAllSchedules, readMySchedule
>>>>>>> 0666ccf (refactor : 작성한 일정의 세부 계획, 동선 출력)
=======
    readInvitations, acceptInvitation, rejectInvitation, createScheduleItems, readAllMySchedules, readMySchedule, readAllSchedules, readBoardSchedule
>>>>>>> 6677a0e (refactor : 커뮤니티게시판 스케줄 목록, 세부 일정 출력, my page의 기타 페이지 html, css)
};

