'use strict';

angular.module('stepApp')
    .factory('EmployeeLoanBillRegister', function ($resource, DateUtils) {
        return $resource('api/employeeLoanBillRegisters/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.issueDate = DateUtils.convertLocaleDateFromServer(data.issueDate);
                    data.createDate = DateUtils.convertLocaleDateFromServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateFromServer(data.updateDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.issueDate = DateUtils.convertLocaleDateToServer(data.issueDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.issueDate = DateUtils.convertLocaleDateToServer(data.issueDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            }
        });
    })

    .factory('InstEmployeeByID', function ($resource) {
        return $resource('api/instEmployees/:id', {}, {
            'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            }
        });
    })

    .factory('FindBillRegisterByLoanRequiID', function ($resource) {
        return $resource('api/employeeLoanBillRegisters/findLoanBillRegister/:loanRequisitionID', {}, {
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
    })

    .factory('FindBillRegisterByBillNo', function ($resource) {
        return $resource('api/employeeLoanBillRegisters/findLoanBillRegisterByBillNo/:billNo', {}, {
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



