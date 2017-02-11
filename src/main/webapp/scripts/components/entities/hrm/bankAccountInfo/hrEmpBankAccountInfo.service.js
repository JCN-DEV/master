'use strict';

angular.module('stepApp')
    .factory('HrEmpBankAccountInfo', function ($resource, DateUtils) {
        return $resource('api/hrEmpBankAccountInfos/:id', {}, {
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
    })
    .factory('HrEmpBankAccountInfoApprover', function ($resource) {
        return $resource('api/hrEmpBankAccountInfosApprover/:id', {},
            {
                'update': { method: 'POST'},
                'query': { method: 'GET', isArray: true}
            });
    })
    .factory('HrEmpBankSalaryAccountInfo', function ($resource) {
        return $resource('api/hrEmpBankSalaryAccountInfos/:emplId', {},
            {
                'query': { method: 'GET'}
            });
    });
