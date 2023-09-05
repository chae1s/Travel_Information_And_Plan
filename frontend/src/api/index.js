import axiosInstance from '@/store/interceptor.js';


function registerUser(userData) {
  return axiosInstance.post('signup', userData);
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

export { registerUser, loginUser, createBoard, readBoards };

