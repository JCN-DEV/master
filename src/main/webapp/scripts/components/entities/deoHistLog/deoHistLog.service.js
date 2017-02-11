'use strict';

angular.module('stepApp')
    .factory('DeoHistLog', function ($resource, DateUtils) {
        return $resource('api/deoHistLogs/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.dateCrated = DateUtils.convertLocaleDateFromServer(data.dateCrated);
                    data.dateModified = DateUtils.convertLocaleDateFromServer(data.dateModified);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.dateCrated = DateUtils.convertLocaleDateToServer(data.dateCrated);
                    data.dateModified = DateUtils.convertLocaleDateToServer(data.dateModified);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.dateCrated = DateUtils.convertLocaleDateToServer(data.dateCrated);
                    data.dateModified = DateUtils.convertLocaleDateToServer(data.dateModified);
                    return angular.toJson(data);
                }
            }
        });
    });
