'use strict';

angular.module('stepApp')
    .factory('EmployeeLoanApproveAndForward', function ($resource, DateUtils) {
        return $resource('api/employeeLoanApproveAndForwards/:id', {}, {
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
                    return angular.toJson(data);
                }
            }
        });
    }).factory('EmployeeLoanDecline', function ($resource, DateUtils) {
        return $resource('api/employeeLoanApproveAndForwards/employeeLoanDecline', {}, {

            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            }
        });
    }).factory('EmployeeLoanReject', function ($resource, DateUtils) {
            return $resource('api/employeeLoanApproveAndForwards/employeeLoanReject', {}, {

                'save': {
                    method: 'POST',
                    transformRequest: function (data) {
                        data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                        data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                        return angular.toJson(data);
                    }
                }
            });
        })

    .factory('Authority', function ($resource) {
            return $resource('api/employeeLoanApproveAndForwards/findUserAuthority', {}, {
                'query': { method: 'GET', isArray: true}
            });
    })
    .factory('GetApproveAmountByReqCode', function ($resource) {
        return $resource('api/employeeLoanApproveAndForwards/GetApproveAmountByReqCode/:loanReqCode', {}, {
            'get': { method: 'GET', isArray: false}
        });
    })
    .factory('GetApproveDataByRequisitionId', function ($resource) {
        return $resource('api/employeeLoanApproveAndForwards/getByRequisitionId/:loanReqId', {}, {
            'query': { method: 'GET', isArray: true}
        });
    })
    .factory('GetForwardMessageByReqIdAndApproveStatus', function ($resource) {
        return $resource('api/employeeLoanApproveAndForwards/GetForwardMessage/:approveStatus', {}, {
            'query': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            }
        });
    })
    .factory('GetEmployeeLoanVocApprovalRole', function ($resource) {
        return $resource('api/employeeLoanApproveAndForwards/getEmployeeLoanForVocApprovalRole', {}, {
            'query': { method: 'GET', isArray: true}
        });
    })
    .factory('GetEmployeeLoanOthersApprovalRole', function ($resource) {
        return $resource('api/employeeLoanApproveAndForwards/getEmployeeLoanForOthersApprovalRole', {}, {
            'query': { method: 'GET', isArray: true}
        });
    })
    .factory('GetEmployeeLoanDteApprovalRole', function ($resource) {
        return $resource('api/employeeLoanApproveAndForwards/getEmployeeLoanForDteApprovalRole', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
