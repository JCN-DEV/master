'use strict';

angular.module('stepApp')
    .factory('HrEducationInfoSearch', function ($resource) {
        return $resource('api/_search/hrEducationInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
