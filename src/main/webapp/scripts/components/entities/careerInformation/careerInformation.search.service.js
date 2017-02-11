'use strict';

angular.module('stepApp')
    .factory('CareerInformationSearch', function ($resource) {
        return $resource('api/_search/careerInformations/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
