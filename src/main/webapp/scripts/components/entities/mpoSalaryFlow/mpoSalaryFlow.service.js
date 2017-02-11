'use strict';

angular.module('stepApp')
    .factory('MpoSalaryFlow', function ($resource, DateUtils) {
        return $resource('api/mpoSalaryFlows/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.dteApproveDate = DateUtils.convertLocaleDateFromServer(data.dteApproveDate);
                    data.ministryApproveDate = DateUtils.convertLocaleDateFromServer(data.ministryApproveDate);
                    data.agApproveDate = DateUtils.convertLocaleDateFromServer(data.agApproveDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.dteApproveDate = DateUtils.convertLocaleDateToServer(data.dteApproveDate);
                    data.ministryApproveDate = DateUtils.convertLocaleDateToServer(data.ministryApproveDate);
                    data.agApproveDate = DateUtils.convertLocaleDateToServer(data.agApproveDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.dteApproveDate = DateUtils.convertLocaleDateToServer(data.dteApproveDate);
                    data.ministryApproveDate = DateUtils.convertLocaleDateToServer(data.ministryApproveDate);
                    data.agApproveDate = DateUtils.convertLocaleDateToServer(data.agApproveDate);
                    return angular.toJson(data);
                }
            }
        });
    });
