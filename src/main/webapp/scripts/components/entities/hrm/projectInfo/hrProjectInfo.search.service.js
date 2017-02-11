'use strict';

angular.module('stepApp')
    .factory('HrProjectInfoSearch', function ($resource) {
        return $resource('api/_search/hrProjectInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
