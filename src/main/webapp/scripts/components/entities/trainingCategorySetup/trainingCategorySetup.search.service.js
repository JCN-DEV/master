'use strict';

angular.module('stepApp')
    .factory('TrainingCategorySetupSearch', function ($resource) {
        return $resource('api/_search/trainingCategorySetups/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
