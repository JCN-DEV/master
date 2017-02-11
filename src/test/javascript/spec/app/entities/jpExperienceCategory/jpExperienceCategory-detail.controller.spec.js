'use strict';

describe('JpExperienceCategory Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockJpExperienceCategory;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockJpExperienceCategory = jasmine.createSpy('MockJpExperienceCategory');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'JpExperienceCategory': MockJpExperienceCategory
        };
        createController = function() {
            $injector.get('$controller')("JpExperienceCategoryDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:jpExperienceCategoryUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
