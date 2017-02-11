'use strict';

angular.module('stepApp')
    .factory('EducationalQualificationSearch', function ($resource) {
        return $resource('api/_search/educationalQualifications/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    })
    .factory('EducationalQualificationEmployee', function ($resource) {
        return $resource('api/educationalQualifications/employee/:id', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
