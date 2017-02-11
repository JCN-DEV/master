'use strict';

angular.module('stepApp')
    .factory('NameCnclApplicationEditLog', function ($resource, DateUtils) {
        return $resource('api/nameCnclApplicationEditLogs/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.created_date = DateUtils.convertLocaleDateFromServer(data.created_date);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.created_date = DateUtils.convertLocaleDateToServer(data.created_date);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.created_date = DateUtils.convertLocaleDateToServer(data.created_date);
                    return angular.toJson(data);
                }
            }
        });
    });
