'use strict';

angular.module('stepApp')
    .factory('InsAcademicInfoSearch', function ($resource) {
        return $resource('api/_search/insAcademicInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
