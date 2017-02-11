'use strict';

angular.module('stepApp')
    .factory('EmployeeLoanCheckRegister', function ($resource, DateUtils) {
        return $resource('api/employeeLoanCheckRegisters/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.createDate = DateUtils.convertLocaleDateFromServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateFromServer(data.updateDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    data.issueDate = DateUtils.convertLocaleDateToServer(data.issueDate);
                    return angular.toJson(data);
                }
            }
        });
    })
    .factory('FindCheckRegisterByCheckNumber', function ($resource) {
        return $resource('api/employeeLoanCheckRegisters/findByCheckNumber/:checkNumber', {}, {
            // 'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if(data != null){
                        console.log(data);
                    }
                    data = angular.fromJson(data);
                    return data;
                }
            }
        });
    });

