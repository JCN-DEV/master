'use strict';

angular.module('stepApp')
    .factory('TrainingSearch', function ($resource) {
        return $resource('api/_search/trainings/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    })
    .factory('TrainingEmployee', function ($resource) {
        return $resource('api/trainings/employee/:id', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
