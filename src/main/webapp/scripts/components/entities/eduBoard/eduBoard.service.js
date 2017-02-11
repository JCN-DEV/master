'use strict';

angular.module('stepApp')
    .factory('EduBoard', function ($resource, DateUtils) {
        return $resource('api/eduBoards/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }).factory('EduBoardByType', function ($resource) {
        return $resource('api/eduBoards/boardType/:boardType', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
