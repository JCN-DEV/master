'use strict';

angular.module('stepApp')
    .factory('MpoCommitteeDescision', function ($resource, DateUtils) {
        return $resource('api/mpoCommitteeDescisions/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    return angular.toJson(data);
                }
            },
            'update': { method:'PUT' }
        });
    });
