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


export {
    registerUser, loginUser, createBoard, readBoards, createSchedule, readSchedule, readLikedItemBySido, createRouteList,
    readInvitations, acceptInvitation, rejectInvitation, createScheduleItems, readAllMySchedules, readMySchedule, readAllSchedules, readBoardSchedule
};

