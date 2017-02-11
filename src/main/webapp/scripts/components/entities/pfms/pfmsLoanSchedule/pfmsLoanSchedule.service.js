'use strict';

angular.module('stepApp')
    .factory('PfmsLoanSchedule', function ($resource, DateUtils) {
        return $resource('api/pfmsLoanSchedules/:id', {}, {
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
            },
            'saveAll': {
                method: 'POST',
                transformRequest: function (data) {
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            }
        });
    }).factory('PfmsLoanScheduleListByEmployee', function ($resource)
    {
        return $resource('api/pfmsLoanScheduleListByEmployee/:employeeInfoId', {},
            {
                'get': { method: 'GET', isArray: true}
            });
    }).factory('PfmsLoanScheduleListByEmployeeAndApp', function ($resource)
    {
        return $resource('api/pfmsLoanScheduleListByEmployeeAndApp/:employeeInfoId/:pfmsLoanAppId', {},
            {
                'get': { method: 'GET', isArray: true}
            });
    }).factory('PfmsLoanScheduleListByEmpAppYearMonth', function ($resource)
    {
        return $resource('api/pfmsLoanScheduleListByEmpAppYearMonth/:employeeInfoId/:pfmsLoanAppId/:loanYear/:loanMonth', {},
            {
                'get': { method: 'GET'}
            });
    });
