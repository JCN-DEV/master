'use strict';

angular.module('stepApp')
    .factory('JpAcademicQualificationSearch', function ($resource) {
        return $resource('api/_search/jpAcademicQualifications/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('AcademicQualificationJpEmployee', function ($resource) {
        return $resource('api/academicQualifications/jpEmployee/:id', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
