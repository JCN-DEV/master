'use strict';

angular.module('stepApp')
    .factory('IisCourseInfoTempSearch', function ($resource) {
        return $resource('api/_search/iisCourseInfoTemps/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
