'use strict';

angular.module('stepApp')
    .factory('InsAcademicInfoTempSearch', function ($resource) {
        return $resource('api/_search/insAcademicInfoTemps/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
