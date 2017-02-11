'use strict';

angular.module('stepApp')
    .factory('PfmsLoanApplication', function ($resource, DateUtils) {
        return $resource('api/pfmsLoanApplications/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.lastInstallmentReDate = DateUtils.convertLocaleDateFromServer(data.lastInstallmentReDate);
                    data.applicationDate = DateUtils.convertLocaleDateFromServer(data.applicationDate);
                    data.createDate = DateUtils.convertLocaleDateFromServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateFromServer(data.updateDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.lastInstallmentReDate = DateUtils.convertLocaleDateToServer(data.lastInstallmentReDate);
                    data.applicationDate = DateUtils.convertLocaleDateToServer(data.applicationDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.lastInstallmentReDate = DateUtils.convertLocaleDateToServer(data.lastInstallmentReDate);
                    data.applicationDate = DateUtils.convertLocaleDateToServer(data.applicationDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            }
        });
    })
    .factory('PfmsLoanApplicationByEmployee', function ($resource)
    {
        return $resource('api/pfmsLoanApplicationByEmployee/:employeeId', {},
            {
                'get': { method: 'GET', isArray: true}
            });
    });
