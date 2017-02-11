'use strict';

angular.module('stepApp')
    .factory('RisNewAppFormTwoSearch', function ($resource) {
        return $resource('api/_search/risNewAppFormTwos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
