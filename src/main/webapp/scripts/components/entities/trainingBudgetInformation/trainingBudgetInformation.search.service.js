'use strict';

angular.module('stepApp')
    .factory('TrainingBudgetInformationSearch', function ($resource) {
        return $resource('api/_search/trainingBudgetInformations/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
