'use strict';

angular.module('stepApp')
    .factory('ProfessionalQualificationSearch', function ($resource) {
        return $resource('api/_search/professionalQualifications/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
