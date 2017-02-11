'use strict';

describe('JpLanguageProficiency Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockJpLanguageProficiency, MockJpEmployee;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockJpLanguageProficiency = jasmine.createSpy('MockJpLanguageProficiency');
        MockJpEmployee = jasmine.createSpy('MockJpEmployee');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'JpLanguageProficiency': MockJpLanguageProficiency,
            'JpEmployee': MockJpEmployee
        };
        createController = function() {
            $injector.get('$controller')("JpLanguageProficiencyDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:jpLanguageProficiencyUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
