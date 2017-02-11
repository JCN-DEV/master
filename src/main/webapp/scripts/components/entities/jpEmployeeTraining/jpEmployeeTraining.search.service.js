'use strict';

angular.module('stepApp')
    .factory('JpEmployeeTrainingSearch', function ($resource) {
        return $resource('api/_search/jpEmployeeTrainings/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('JpEmployeeTrainingJpEmployee', function ($resource) {
        return $resource('api/trainings/jpEmployee/:id', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
