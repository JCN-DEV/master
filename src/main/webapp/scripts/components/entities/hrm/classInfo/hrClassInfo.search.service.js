'use strict';

angular.module('stepApp')
    .factory('HrClassInfoSearch', function ($resource) {
        return $resource('api/_search/hrClassInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
