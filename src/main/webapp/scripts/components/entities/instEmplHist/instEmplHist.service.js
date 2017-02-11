'use strict';

angular.module('stepApp')
    .factory('InstEmplHist', function ($resource, DateUtils) {
        return $resource('api/instEmplHists/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.start = DateUtils.convertLocaleDateFromServer(data.start);
                    data.end = DateUtils.convertLocaleDateFromServer(data.end);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.start = DateUtils.convertLocaleDateToServer(data.start);
                    data.end = DateUtils.convertLocaleDateToServer(data.end);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.start = DateUtils.convertLocaleDateToServer(data.start);
                    data.end = DateUtils.convertLocaleDateToServer(data.end);
                    return angular.toJson(data);
                }
            }
        });
    });
