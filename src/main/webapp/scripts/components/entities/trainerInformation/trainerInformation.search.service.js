'use strict';

angular.module('stepApp')
    .factory('TrainerInformationSearch', function ($resource) {
        return $resource('api/_search/trainerInformations/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
