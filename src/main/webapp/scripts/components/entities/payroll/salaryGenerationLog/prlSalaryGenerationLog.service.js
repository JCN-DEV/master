'use strict';

angular.module('stepApp')
    .factory('PrlSalaryGenerationLog', function ($resource, DateUtils) {
        return $resource('api/prlSalaryGenerationLogs/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.generateDate = DateUtils.convertLocaleDateFromServer(data.generateDate);
                    data.createDate = DateUtils.convertLocaleDateFromServer(data.createDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.generateDate = DateUtils.convertLocaleDateToServer(data.generateDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.generateDate = DateUtils.convertLocaleDateToServer(data.generateDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    return angular.toJson(data);
                }
            }
        });
    });
