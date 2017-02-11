'use strict';

angular.module('stepApp')
    .factory('EduBoardSearch', function ($resource) {
        return $resource('api/_search/eduBoards/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
