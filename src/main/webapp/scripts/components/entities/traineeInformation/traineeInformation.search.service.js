'use strict';

angular.module('stepApp')
    .factory('TraineeInformationSearch', function ($resource) {
        return $resource('api/_search/traineeInformations/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
