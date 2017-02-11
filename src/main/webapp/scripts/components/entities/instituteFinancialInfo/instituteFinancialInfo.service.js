'use strict';

angular.module('stepApp')
    .factory('InstituteFinancialInfo', function ($resource, DateUtils) {
        return $resource('api/instituteFinancialInfos/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.issueDate = DateUtils.convertLocaleDateFromServer(data.issueDate);
                    data.expireDate = DateUtils.convertLocaleDateFromServer(data.expireDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.issueDate = DateUtils.convertLocaleDateToServer(data.issueDate);
                    data.expireDate = DateUtils.convertLocaleDateToServer(data.expireDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.issueDate = DateUtils.convertLocaleDateToServer(data.issueDate);
                    data.expireDate = DateUtils.convertLocaleDateToServer(data.expireDate);
                    return angular.toJson(data);
                }
            }
        });
    });
