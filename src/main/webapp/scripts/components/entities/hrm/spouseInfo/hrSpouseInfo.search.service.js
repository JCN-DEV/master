'use strict';

angular.module('stepApp')
    .factory('HrSpouseInfoSearch', function ($resource) {
        return $resource('api/_search/hrSpouseInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
