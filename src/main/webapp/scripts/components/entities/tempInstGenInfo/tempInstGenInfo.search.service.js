'use strict';

angular.module('stepApp')
    .factory('TempInstGenInfoSearch', function ($resource) {
        return $resource('api/_search/tempInstGenInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
