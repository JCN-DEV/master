'use strict';

angular.module('stepApp')
    .factory('HrEntertainmentBenefitSearch', function ($resource) {
        return $resource('api/_search/hrEntertainmentBenefits/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
