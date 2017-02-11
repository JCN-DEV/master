'use strict';

angular.module('stepApp')
    .factory('EduLevelSearch', function ($resource) {
        return $resource('api/_search/eduLevels/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('ActiveEduLevels', function ($resource) {
        return $resource('api/eduLevels/active', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
