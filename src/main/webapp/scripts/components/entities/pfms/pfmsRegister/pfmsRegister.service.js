'use strict';

angular.module('stepApp')
    .factory('PfmsRegister', function ($resource, DateUtils) {
        return $resource('api/pfmsRegisters/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.billIssueDate = DateUtils.convertLocaleDateFromServer(data.billIssueDate);
                    data.billDate = DateUtils.convertLocaleDateFromServer(data.billDate);
                    data.checkDate = DateUtils.convertLocaleDateFromServer(data.checkDate);
                    data.createDate = DateUtils.convertLocaleDateFromServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateFromServer(data.updateDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.billIssueDate = DateUtils.convertLocaleDateToServer(data.billIssueDate);
                    data.billDate = DateUtils.convertLocaleDateToServer(data.billDate);
                    data.checkDate = DateUtils.convertLocaleDateToServer(data.checkDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.billIssueDate = DateUtils.convertLocaleDateToServer(data.billIssueDate);
                    data.billDate = DateUtils.convertLocaleDateToServer(data.billDate);
                    data.checkDate = DateUtils.convertLocaleDateToServer(data.checkDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            }
        });
    }).factory('PfmsRegisterListByEmployee', function ($resource)
    {
        return $resource('api/pfmsRegisterListByEmployee/:employeeInfoId', {},
            {
                'get': { method: 'GET', isArray: true}
            });
    });
