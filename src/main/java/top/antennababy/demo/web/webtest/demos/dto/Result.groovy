package top.antennababy.demo.web.webtest.demos.dto

class Result<T> {
    def code;
    def message;
    def trackId;
    T data;
    static Result success(data){
        return new Result(code: 200, message: "success", data: data)
    }
    static Result fail(code, message){
        return new Result(code: code, message: message, data: null)
    }
}
