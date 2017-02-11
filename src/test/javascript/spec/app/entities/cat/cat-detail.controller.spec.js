'use strict';

describe('Cat Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockCat, MockJob;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockCat = jasmine.createSpy('MockCat');
        MockJob = jasmine.createSpy('MockJob');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Cat': MockCat,
            'Job': MockJob
        };
        createController = function() {
            $injector.get('$controller')("CatDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:catUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
